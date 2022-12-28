package projectManagementSystem.filters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.service.UserRoleService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

public class PermissionFilter implements Filter {
    public static final Logger logger = LogManager.getLogger(PermissionFilter.class);
    private final UserRoleService userRoleService;

    public PermissionFilter(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * Processes a request/response pair passed through the Filter Chain due to a client request for a resource at the end of the chain.
     * The userId and boardId headers of the request are being checked.
     * if userId is authorized for performing the required action (determined by the url path) in boardId,
     * this filter passes on the request and response to the next entity in the chain.
     * If not - the filter return an Unauthorized response.
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("Permission filter is working on the following request: " + servletRequest);

        MutableHttpServletRequest req = new MutableHttpServletRequest((HttpServletRequest) servletRequest);
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();

        boolean isAuthorized = true;

        if (actionRequiresAuthorization(url)) {
            BoardAction action = url.contains("updateItem") ?
                    BoardAction.valueOf(req.getHeader("action")) : BoardAction.getByRoute(url);

            long userId, boardId;
            try {
                userId = (long) req.getAttribute("userId");
                boardId = parseLong(req.getHeader("boardId"));
            } catch (Exception e) {
                userId = 0L;
                boardId = 0L;
            }

            if (!userRoleService.isAuthorized(boardId, userId, action)) {
                returnBadResponse(res);
                isAuthorized = false;
            }
        }

        if (isAuthorized) {
            filterChain.doFilter(req, res);
        }
    }

    private boolean actionRequiresAuthorization(String url) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("configuration.json");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<String>> configMap = mapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {});
        return configMap.get("authorization-routes").stream().anyMatch(url::contains);
    }


    /**
     * Sends an error response to the client using status code 401, with message Unauthorized.
     *
     * @param res, HttpServletResponse object, contains response to a servlet request.
     * @throws IOException, if an input or output exception occurs.
     */
    private void returnBadResponse(HttpServletResponse res) throws IOException {
        res.sendError(403, "Forbidden");
    }

    /**
     * indicate to a filter that it is being taken out of service.
     * This method is only called once all threads within the filter's doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the doFilter method again on this instance of the filter.
     * This method gives the filter an opportunity to clean up any resources that are being held.
     */
    public void destroy() {
        Filter.super.destroy();
    }
}