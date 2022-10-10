import { createGlobalState } from 'react-hooks-global-state'

export let{ getGlobalState, setGlobalState } = createGlobalState({
    // This is for local testing, comment out when deploying
    // backendDomain: 'http://localhost:8080',

    // Below is for AWS deployment, uncomment when deploying
    backendDomain: 'https://sec-backend.s3843790-cc.com'
})
