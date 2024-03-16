import {HttpConnector} from "../http-connector/http-connector.js";

export class DocumentGateway {

    api;

    static INSTANCE;

    constructor() {
        this.api = HttpConnector.getInstance();
    }

    uploadEmployeeRegistry(file) {
        const formData = new FormData();
        formData.append('file', file);
        return this.api.postQuery('document/employee-registry', formData);
    }

    static getInstance() {
        if (!this.INSTANCE) {
            this.INSTANCE = new DocumentGateway();
        }
        return this.INSTANCE;
    }

}
