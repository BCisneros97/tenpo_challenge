import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";

export default function Nav() {
  return (
    <Navbar bg="black">
      <Container>
        <Navbar.Brand href="#home">
          <img
            src="/tenpo_logo.svg"
            className="d-inline-block align-top navbar__logo"
            alt="Tenpo logo"
            style={{ width: "10em" }}
          />
        </Navbar.Brand>
      </Container>
    </Navbar>
  );
}
