import {uploadFile} from "../../utils/file/file-utils.js";
import {DocumentGateway} from "../../adapters/document/document-gateway.js";

class DocumentService {

    documentGateway;

    constructor(documentGateway) {
        this.documentGateway = documentGateway;
    }

    async uploadRegistry() {
        const files = await uploadFile();
        if (files && files.length) {
            app.globalSpinner.show();
            const resp = await this.documentGateway.uploadEmployeeRegistry(files[0]);
            app.globalSpinner.hide();
            return await resp.json();
        }
    }
}


export const documentServiceInstance = new DocumentService(DocumentGateway.getInstance());
