import {DocumentGateway} from "./adapters/document/document-gateway.js";
import {globalSpinner} from "./global-spinner.js";

class Application {

    documentGateway;
    globalSpinner;

    constructor() {
        this.documentGateway = DocumentGateway.getInstance();
        this.globalSpinner = globalSpinner;
    }
}



export const App = new Application();
