const path = require('path');
const Pact = require('@pact-foundation/pact').Pact;

global.pactPort = 38080;
global.pactUrl= 'localhost';
global.provider = new Pact({
    cors: true,
    port: global.pactPort,
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    loglevel: 'debug',
    dir: path.resolve(process.cwd()+'/../src/test/resources', 'pacts'),
    spec: 2,
    pactfileWriteMode: 'update',
    consumer: 'dummyservice-ui',
    provider: 'dummyservice-api',
    host: global.pactUrl
});