package teamproject.lam_server.document;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@ActiveProfiles("local")
public class CategoryApiDocsTest {
    private MockMvc mockMvc;

//    @Test
//    public void categories() throws Exception {
//        this.mockMvc.perform(
//                        get("/docs")
//                                .accept(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo(document("common",
//                        customResponseFields("custom-response", beneathPath("data.genders").withSubsectionId("genders"), // (2)
//                                attributes(key("title").value("성별")),
//                                enumConvertFieldDescriptor(Gender.values())
//                        )
//                ));
//    }

}
