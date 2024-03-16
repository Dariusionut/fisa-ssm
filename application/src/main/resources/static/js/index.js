"use strict";

import {uploadFile} from "./utils/file/file-utils.js";
import {App} from "./app.js";

window.app = App

const fileUploadBtn = document.getElementById('file-upload')


const fileUploadHandler = async () => {
    const files = await uploadFile();
    if (files && files.length) {
        app.globalSpinner.show();
        app.documentGateway.uploadEmployeeRegistry(files[0])
            .then(resp => resp.json())
            .then(resp => {
                const employees = document.getElementById('employees');
                employees.innerHTML = '';
                resp.forEach(emp => {
                    const p = document.createElement('p');
                    p.innerText = `${emp.lastName} ${emp.firstName} -- ${emp.cnp}`;
                    employees.appendChild(p);
                    app.globalSpinner.hide();
                })
            })
    }
}
fileUploadBtn.addEventListener('click', fileUploadHandler);

