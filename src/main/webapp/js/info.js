class Info extends HTMLElement {

    static componentName = 'app-info';

    constructor() {
        super();
        this.root = this.attachShadow({mode: "open"});
    }

    /** Callbacks */
    connectedCallback() {
        let openApi = this.getAttribute("openApi");
        let swaggerUi = this.getAttribute("swaggerUi");
        this.root.innerHTML = `
            <link rel="stylesheet" href="css/bootstrap.min.css"/>
            <div class="jumbotron p-2 p-md-3 text-white rounded bg-dark border-danger border-top">
                <div class="col-md-6 px-0">
                    <h1 class="display-5 font-italic">Hello from JEE Playground!</h1>
                    <p class="lead my-3">For evaluation please use the following to play with:
                        <a class="btn  btn-warning btn-sm" href="${openApi}" role="button">Open Api</a> 
                        <a class="btn  btn-warning btn-sm" href="${swaggerUi}" role="button">Swagger UI</a>
                     </p>
                </div>
            </div>
        `
    }

    static get observedAttributes() {
        return ['openApi', 'swaggerUi'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        console.log(`${name}'s value has been changed from ${oldValue} to ${newValue}`);
    }
}
/** Register */
customElements.define(Info.componentName, Info);
