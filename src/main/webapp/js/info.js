class Info extends HTMLElement {

    constructor() {
        super();
        this.root = this.attachShadow({mode: "open"});
    }

    connectedCallback() {
        this.root.innerHTML = `
            <h1>Hello from JEE Playground!</h1>
            See <a href="http://localhost:8080/openapi/">Api docs</a> or 
            <a href="http://localhost:8080/micro-playground/api/openapi-ui/">Swagger UI</a> 
            for things to play with.</p>
        `
    }
}

customElements.define('an-info', Info);