import { Button, ListGroup } from "react-bootstrap";
import type Transaction from "../models/transaction";
import moment from "moment";
import Swal from "sweetalert2";
import { deleteTransaction } from "../api/transactions";

export default function TransactionItem({
  transaction,
  onEdit,
  onDelete,
}: {
  transaction: Transaction;
  onEdit: (transaction: Transaction) => void;
  onDelete: () => void;
}) {
  const transactionDate = moment(transaction.transactionDate + "Z").format(
    "DD/MM/YYYY hh:mm:ss a"
  );

  function handleEdit() {
    onEdit(transaction);
  }

  function handleDelete() {
    Swal.fire({
      title: "¿Está seguro de borrar esta transacción?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Sí, borrar",
      cancelButtonText: "Cancelar",
    }).then((result) => {
      if (result.isConfirmed) {
        deleteTrans();
      }
    });
  }

  async function deleteTrans() {
    try {
      await deleteTransaction(transaction.id);
      onDelete();
      Swal.fire({
        icon: "success",
        title: "Transacción borrada",
        text: "La transacción ha sido borrada correctamente",
        timer: 3000,
      });
    }
    catch (error) {
      console.error("Error deleting transaction:", error);
      Swal.fire('Error', 'Ocurrió un error al borrar la transacción', 'error');
    }
  }

  return (
    <ListGroup.Item
      as="li"
      className="d-flex justify-content-between align-items-start">
      <div className="transaction-detail">
        <div className="ms-2 me-auto">
          <div className="fw-bold mb-1">Transacción #{transaction.id}</div>
          <div>Monto: ${transaction.amount}</div>
          <div>Giro o comercio: {transaction.businessSector}</div>
          <div>Tenpista: {transaction.clientName}</div>
          <div>Fecha: {transactionDate}</div>
        </div>
      </div>

      <div className="d-flex flex-column align-items-center justify-content-center gap-2">
        <Button variant="warning" onClick={handleEdit}>
          Editar
        </Button>
        <Button variant="danger" onClick={handleDelete}>
          Eliminar
        </Button>
      </div>
    </ListGroup.Item>
  );
}
