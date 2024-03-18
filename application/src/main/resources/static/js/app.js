import {DocumentGateway} from "./adapters/document/document-gateway.js";
import {globalSpinner} from "./global-spinner.js";
import {documentServiceInstance} from "./domain/service/document-service.js";

class Application {

    documentService;
    globalSpinner;

    constructor() {
        this.documentService = documentServiceInstance;
        this.globalSpinner = globalSpinner;
    }
}


export const App = new Application();
