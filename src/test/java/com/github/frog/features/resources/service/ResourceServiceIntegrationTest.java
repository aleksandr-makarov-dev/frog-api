package com.github.frog.features.resources.service;

import com.github.frog.features.resources.ResourceTestData;
import com.github.frog.features.resources.dto.ResourceResponse;
import com.github.frog.features.resources.exception.ResourceNotFound;
import com.github.frog.features.resources.repository.ResourceRepository;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.messages.Item;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FilenameUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ResourceServiceIntegrationTest {

    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");
    static final MinIOContainer minIOContainer = new MinIOContainer("minio/minio")
            .withUserName("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        registry.add("spring.minio.endpoint", minIOContainer::getS3URL);
        registry.add("spring.minio.access-key", minIOContainer::getUserName);
        registry.add("spring.minio.secret-key", minIOContainer::getPassword);
    }

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucket;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        minIOContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
        minIOContainer.stop();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        resourceRepository.deleteAll();

        Iterable<Result<Item>> items = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucket)
                        .prefix("/")
                        .build()
        );

        for (Result<Item> item : items) {
            String objectName = item.get().objectName();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .build()
            );
        }
    }

    @Test
    @DisplayName("Given valid multipart file, when uploadResource, then return resource response")
    public void givenValidMultipartFile_whenUploadResource_thenReturnResourceResponse() {
        // given
        MultipartFile file = ResourceTestData.mockMultipartFile();

        // when
        ResourceResponse response = resourceService.createResource(file);

        // then
        assertThat(response).isNotNull();
        assertThat(response.originalName()).isEqualTo(FilenameUtils.getBaseName(file.getOriginalFilename()));
        assertThat(response.size()).isEqualTo(file.getSize());
        assertThat(response.downloadUrl()).isNotEmpty();
    }

    @Test
    @DisplayName("Given valid resource id, when getResourceById, then return resource response")
    public void givenValidResourceId_whenGetResourceById_thenReturnResourceResponse() {
        // given
        MultipartFile file = ResourceTestData.mockMultipartFile();
        ResourceResponse resource = resourceService.createResource(file);

        // when
        ResourceResponse response = resourceService.getResourceById(resource.id());

        // then
        assertThat(response).isNotNull();
        assertThat(response.originalName()).isEqualTo(FilenameUtils.getBaseName(file.getOriginalFilename()));
        assertThat(response.size()).isEqualTo(file.getSize());
        assertThat(response.downloadUrl()).isNotEmpty();
    }

    @Test
    @DisplayName("Given invalid resource id, when getResourceById, then throw ResourceNotFoundException")
    public void givenInvalidResourceId_whenGetResourceById_thenThrowResourceNotFoundException() {
        // given
        MultipartFile file = ResourceTestData.mockMultipartFile();
        resourceService.createResource(file);

        Long invalidResourceId = 999L;

        // when
        assertThatThrownBy(() -> resourceService.getResourceById(invalidResourceId))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("Resource with ID=%d not found", invalidResourceId);

        // then
        assertThat(resourceRepository.findById(invalidResourceId)).isEmpty();
    }
}