package com.example.tea.catalog.adapter.in.graphql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeaGraphqlIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private GraphQlTester graphQlTester() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port + "/graphql")
                .build();
        return HttpGraphQlTester.create(client);
    }

    @Test
    void queriesTeasByType() {
        graphQlTester().document("""
                query {
                  teasByType(type: GREEN) {
                    name
                    type
                    brand {
                      name
                    }
                  }
                }
                """)
                .execute()
                .path("teasByType[0].name").entity(String.class).isEqualTo("Jeju Green Tea")
                .path("teasByType[0].brand.name").entity(String.class).isEqualTo("Osulloc");
    }

    @Test
    void createsBrandAndTeaThroughMutations() {
        Long brandId = graphQlTester().document("""
                mutation {
                  createBrand(input: {name: "Mariage Freres", country: "France"}) {
                    id
                    name
                  }
                }
                """)
                .execute()
                .path("createBrand.id").entity(Long.class).get();

        assertThat(brandId).isNotNull();

        graphQlTester().document("""
                mutation CreateTea($brandId: ID!) {
                  createTea(
                    input: {
                      name: "Marco Polo"
                      type: BLACK
                      brandId: $brandId
                      originCountry: "China"
                      caffeine: true
                      description: "Floral and fruity black tea"
                      rating: 4.7
                    }
                  ) {
                    name
                    type
                    brand {
                      name
                    }
                  }
                }
                """)
                .variable("brandId", brandId)
                .execute()
                .path("createTea.name").entity(String.class).isEqualTo("Marco Polo")
                .path("createTea.brand.name").entity(String.class).isEqualTo("Mariage Freres");
    }

    @Test
    void returnsGraphQlErrorWhenBrandIsMissing() {
        graphQlTester().document("""
                mutation {
                  createTea(
                    input: {
                      name: "Missing Brand Tea"
                      type: BLACK
                      brandId: 999
                      caffeine: true
                    }
                  ) {
                    id
                  }
                }
                """)
                .execute()
                .errors()
                .satisfy(errors -> assertThat(errors)
                        .anyMatch(error -> error.getMessage() != null && error.getMessage().contains("Brand not found: 999")));
    }

    @Test
    void returnsGraphQlErrorWhenBrandAlreadyExists() {
        graphQlTester().document("""
                mutation {
                  createBrand(input: {name: "Osulloc", country: "Korea"}) {
                    id
                  }
                }
                """)
                .execute()
                .errors()
                .satisfy(errors -> assertThat(errors)
                        .anyMatch(error -> error.getMessage() != null && error.getMessage().contains("Brand already exists: Osulloc")));
    }

    @Test
    void updatesTeaThroughMutation() {
        graphQlTester().document("""
                mutation {
                  updateTea(
                    input: {
                      id: 2
                      name: "Breakfast Reserve"
                      type: BLACK
                      brandId: 2
                      originCountry: "Korea"
                      caffeine: true
                      description: "Updated black tea"
                      rating: 4.9
                    }
                  ) {
                    id
                    name
                    brand {
                      name
                    }
                    description
                  }
                }
                """)
                .execute()
                .path("updateTea.name").entity(String.class).isEqualTo("Breakfast Reserve")
                .path("updateTea.brand.name").entity(String.class).isEqualTo("Osulloc")
                .path("updateTea.description").entity(String.class).isEqualTo("Updated black tea");
    }

    @Test
    void deletesTeaThroughMutation() {
        graphQlTester().document("""
                mutation {
                  deleteTea(id: 1)
                }
                """)
                .execute()
                .path("deleteTea").entity(Boolean.class).isEqualTo(true);

        graphQlTester().document("""
                query {
                  tea(id: 1) {
                    id
                  }
                }
                """)
                .execute()
                .errors()
                .satisfy(errors -> assertThat(errors)
                        .anyMatch(error -> error.getMessage() != null && error.getMessage().contains("Tea not found: 1")));
    }
}
