import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import moment from "moment";
import { useEffect, useState } from "react";
import { createTransaction, updateTransaction } from "../api/transactions";
import type Client from "../models/client";
import { getClients } from "../api/clients";
import type Transaction from "../models/transaction";
import Swal from "sweetalert2";

export default function TransactionForm({
  show,
  transaction,
  onHide,
  onSave,
}: {
  show: boolean;
  transaction: Transaction | null;
  onHide: () => void;
  onSave: () => void;
}) {
  const [validated, setValidated] = useState(false);
  const [errors, setErrors] = useState<any>({});
  const [clients, setClients] = useState<Client[]>([]);

  const [amount, setAmount] = useState(1);
  const [businessSector, setBusinessSector] = useState("");
  const [clientName, setClientName] = useState("");
  const [transactionDate, setTransactionDate] = useState(
    moment().format("YYYY-MM-DDTHH:mm")
  );

  useEffect(() => {
    const fetchClients = async () => {
      try {
        const data = await getClients();
        setClients(data);
      } catch (error) {
        console.error("Error fetching clients:", error);
      }
    };
    fetchClients();
  }, []);

  useEffect(() => {
    setAmount(transaction?.amount || 1);
    setBusinessSector(transaction?.businessSector || "");
    setClientName(transaction?.clientName || "");
    setTransactionDate(
      moment(
        !!transaction ? transaction.transactionDate + "Z" : undefined
      ).format("YYYY-MM-DDTHH:mm")
    );
    setValidated(false);
    setErrors({});
  }, [transaction]);

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    if (form.checkValidity() === false) {
      return;
    }
    setValidated(true);
    saveTransaction();
  }

  async function saveTransaction() {
    const transactionData = {
      amount,
      businessSector,
      clientName,
      transactionDate: moment(transactionDate).utc().format(),
    };

    try {
      if (transaction) {
        await updateTransaction(transaction.id, transactionData);
      } else {
        await createTransaction(transactionData);
      }
      Swal.fire({
        title: "Transacción guardada",
        icon: "success",
        confirmButtonText: "Aceptar",
        timer: 3000,
      });
      onSave();
    } catch (error: any) {
      console.error("Error saving transaction:", error);
      if (error.response?.status === 403) {
        Swal.fire({
          title: "Error",
          text: "El cliente ha alcanzado el límite de transacciones",
          icon: "error",
          confirmButtonText: "Aceptar",
        });
        return;
      }
      if (error.response?.status === 400) {
        setErrors(error.response?.data.errors);
        setValidated(false);
        return;
      }
      Swal.fire({
        title: "Error",
        text: "Ocurrió un error al guardar la transacción",
        icon: "error",
        confirmButtonText: "Aceptar",
      });
    }
  }

  return (
    <Modal show={show} onHide={onHide} size="lg" centered>
      <Modal.Header closeButton>
        <Modal.Title>
          {transaction?.id
            ? "Transacción #" + transaction.id
            : "Nueva transacción"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form noValidate validated={validated} onSubmit={handleSubmit}>
          <Form.Group className="mb-3" controlId="amount">
            <Form.Label>Monto</Form.Label>
            <Form.Control
              type="number"
              placeholder="Ingrese monto"
              value={amount}
              onChange={(e) => setAmount(Number(e.target.value))}
              required
              min={1}
              step={1}
              isInvalid={!!errors.amount}
            />
            <Form.Control.Feedback type="invalid">
              El monto debe ser un número positivo
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3" controlId="businessSector">
            <Form.Label>Giro o comercio</Form.Label>
            <Form.Control
              type="text"
              placeholder="Ingrese giro o comercio"
              value={businessSector}
              onChange={(e) => setBusinessSector(e.target.value)}
              required
              isInvalid={!!errors.businessSector}
            />
            <Form.Control.Feedback type="invalid">
              El giro o comercio debe tener al menos 2 caracteres
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3" controlId="clientName">
            <Form.Label>Tenpista</Form.Label>
            <Form.Select
              value={clientName}
              onChange={(e) => setClientName(e.target.value)}
              required
              isInvalid={!!errors.clientName}>
              <option>Seleccione un Tenpista</option>
              {clients.map((client) => (
                <option key={client.id} value={client.name}>
                  {client.name}
                </option>
              ))}
            </Form.Select>
            <Form.Control.Feedback type="invalid">
              El nombre del Tenpista no es válido
            </Form.Control.Feedback>
          </Form.Group>

          <Form.Group className="mb-3" controlId="transactionDate">
            <Form.Label>Fecha de transacción</Form.Label>
            <Form.Control
              type="datetime-local"
              value={transactionDate}
              onChange={(e) => setTransactionDate(e.target.value)}
              required
              isInvalid={!!errors.transactionDate}
            />
            <Form.Control.Feedback type="invalid">
              La fecha de transacción debe ser menor o igual a la fecha actual
            </Form.Control.Feedback>
          </Form.Group>

          <Button className="d-block ms-auto" variant="primary" type="submit">
            Guardar
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
}
