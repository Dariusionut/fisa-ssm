class GlobalSpinner {

    spinnerContainer;

    constructor() {
        this.spinnerContainer = document.getElementById('spinner-container');
    }

    show() {
        this.spinnerContainer.classList.remove('d-none')
    }

    hide() {
        this.spinnerContainer.classList.add('d-none')
    }
}

export const globalSpinner = new GlobalSpinner();
