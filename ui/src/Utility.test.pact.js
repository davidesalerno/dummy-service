import {getDataApi} from "./Utility";
import {simpleResponse,simpleRequest} from "./pact.fixtures";
import { PactV3 } from '@pact-foundation/pact';
import path from "path";

// Create a 'pact' between the two applications in the integration we are testing
const provider = new PactV3({
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    dir: path.resolve(process.cwd()+'/../src/test/resources', 'pacts'),
    consumer: 'dummyservice-ui',
    provider: 'dummyservice-api',
});

describe("API Pact test", () => {
    describe("getting simple response", () => {
        it("dummy service simply works", () => {
            // Arrange: Setup our expected interactions
            // We use Pact to mock out the backend API
            provider
                .given('A simple request')
                .uponReceiving('a request without parameters')
                .withRequest(simpleRequest)
                .willRespondWith(simpleResponse);

            return provider.executeTest(async (mockserver) => {
                // Act: test our API client behaves correctly
                //
                // Note we configure the DogService API client dynamically to
                // point to the mock service Pact created for us, instead of
                // the real one
                const data = await getDataApi(mockserver.url);
                // Assert: check the result
                expect(data).toStrictEqual({"name": "api-1",
                    "uri": "/",
                    "type": "HTTP",
                    "ip_addresses": [
                        "172.20.0.2"
                    ],
                    "start_time": "2021-05-02T10:22:50.906984",
                    "end_time": "2021-05-02T10:22:50.912933",
                    "duration": 0,
                    "body": "Hello World 1",
                    "code": 200,
                    "upstream_calls":[
                        {
                            "name": "api-1",
                            "uri": "/",
                            "type": "HTTP",
                            "body": "Hello World 1",
                            "duration": 0,
                            "code": 200,
                            "start_time": "2021-05-02T10:22:50.906+00:00",
                            "end_time": "2021-05-02T10:22:50.912+00:00",
                            "ip_addresses": [
                                "172.20.0.2"
                            ]
                        },
                        {
                            "name": "api-2",
                            "uri": "/",
                            "type": "HTTP",
                            "body": "Hello World 2",
                            "duration": 0,
                            "code": 200,
                            "start_time": "2021-05-02T10:22:50.906+00:00",
                            "end_time": "2021-05-02T10:22:50.912+00:00",
                            "ip_addresses": [
                                "172.20.0.2"
                            ]
                        }
                    ]});
            });

        });
    });
});