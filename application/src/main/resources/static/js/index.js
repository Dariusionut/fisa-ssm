"use strict";

import {App} from "./app.js";

window.app = App

const fileUploadBtn = document.getElementById('file-upload')


const fileUploadHandler = async () => {
    const contracts = await app.documentService.uploadRegistry();

    console.log(contracts.map(c => c.job.name).sort());
    app.globalSpinner.hide();
}

fileUploadBtn?.addEventListener('click', fileUploadHandler);

