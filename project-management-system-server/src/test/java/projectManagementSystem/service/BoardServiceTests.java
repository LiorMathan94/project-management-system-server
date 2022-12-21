package projectManagementSystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagementSystem.repository.BoardRepository;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTests {
    @Mock
    private BoardRepository boardRepository;
}
