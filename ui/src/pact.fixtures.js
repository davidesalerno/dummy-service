/* pact.fixtures.js */
import { MatchersV3 } from '@pact-foundation/pact';

export const simpleRequest = {
    method: 'GET',
    path: '/'
};

export const simpleResponse = {
  status: 200,
  headers: {
    'Content-Type': 'application/json'
  },
  body: MatchersV3.like({
    name: "api-1",
    uri: "/",
    type: "HTTP",
    ip_addresses: [
      "172.20.0.2"
    ],
    start_time: "2021-05-02T10:22:50.906984",
    end_time: "2021-05-02T10:22:50.912933",
    duration: 0,
    body: "Hello World 1",
    code: 200,
    upstream_calls: [
      {
        name: "api-1",
        uri: "/",
        type: "HTTP",
        body: "Hello World 1",
        duration: 0,
        code: 200,
        start_time: "2021-05-02T10:22:50.906+00:00",
        end_time: "2021-05-02T10:22:50.912+00:00",
        ip_addresses: [
          "172.20.0.2"
        ]
      },
      {
        name: "api-2",
        uri: "/",
        type: "HTTP",
        body: "Hello World 2",
        duration: 0,
        code: 200,
        start_time: "2021-05-02T10:22:50.906+00:00",
        end_time: "2021-05-02T10:22:50.912+00:00",
        ip_addresses: [
          "172.20.0.2"
        ]
      }
    ]
  }),
};