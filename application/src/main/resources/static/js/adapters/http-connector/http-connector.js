export class HttpConnector {

    #publicUrl = '/fisa-ssm/api/v1/'
    static INSTANCE;

    getQuery(url) {
        return fetch(`${this.#publicUrl}${url}`)
            .then(response => response)
    }

    postQuery(url, body) {
        return fetch(`${this.#publicUrl}${url}`, {
            method: 'POST',
            body: body
        }).then(response => response);
    }

    static getInstance() {
        if (!this.INSTANCE) {
            this.INSTANCE = new HttpConnector();
        }

        return this.INSTANCE;
    }
}
