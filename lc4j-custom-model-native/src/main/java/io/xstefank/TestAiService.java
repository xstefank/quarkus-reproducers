package io.xstefank;

import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface TestAiService {

    TestModel test(String prompt);
}
