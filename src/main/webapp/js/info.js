class Info extends HTMLElement {

    constructor() {
        super();
        this.root = this.attachShadow({mode: "open"});
    }

    connectedCallback() {
        this.root.innerHTML = `
            <link rel="stylesheet" href="css/bootstrap.min.css"/>
            <div class="container">
            <h3>Hello from JEE Playground!</h3>
            <div class="alert alert-primary" role="alert">
            See <a class="btn btn-dark btn-sm" href="http://localhost:8080/openapi/" role="button">Api docs</a> or 
            <a class="btn btn-dark btn-sm" href="http://localhost:8080/micro-playground/api/openapi-ui/" role="button">Swagger UI</a> 
            for things to play with.
            </div>
            </div>
        `
    }
}

customElements.define('an-info', Info);