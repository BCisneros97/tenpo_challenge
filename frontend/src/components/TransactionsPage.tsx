import { useEffect, useState } from "react";
import TransactionItem from "./TransactionItem";
import type Transaction from "../models/transaction";
import { getTransactions } from "../api/transactions";
import Loader from "./Loader";
import ErrorMessage from "./ErrorMessage";
import TransactionForm from "./TransactionForm";
import ListGroup from "react-bootstrap/ListGroup";
import { Button, Container, Row } from "react-bootstrap";

export default function TransactionsPage() {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);
  const [selectedTransaction, setSelectedTransaction] =
    useState<Transaction | null>(null);

  useEffect(() => {
    fetchTransactions();
  }, []);

  async function fetchTransactions() {
    try {
      const data = await getTransactions();
      setTransactions(data);
    } catch (error: any) {
      console.error("Error fetching transactions:", error);
      setError(error.response?.data?.message || error.message);
    } finally {
      setIsLoading(false);
    }
  }

  function handleAddTransaction() {
    setSelectedTransaction(null);
    setShowForm(true);
  }

  function handleSaveTransaction() {
    setShowForm(false);
    setSelectedTransaction(null);
    fetchTransactions();
  }

  function handleEditTransaction(transaction: Transaction) {
    setSelectedTransaction(transaction);
    setShowForm(true);
  }

  return (
    <Container className="py-5">
      <Row>
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2>Transacciones</h2>
          <Button variant="success" onClick={handleAddTransaction}>
            Agregar
          </Button>
        </div>
      </Row>

      <Row>
        {isLoading && <Loader />}
        {!isLoading && !error && (
          <ListGroup>
            {transactions.map((transaction: Transaction) => (
              <TransactionItem
                key={transaction.id}
                transaction={transaction}
                onEdit={handleEditTransaction}
                onDelete={fetchTransactions}
              />
            ))}
          </ListGroup>
        )}
        {error && <ErrorMessage message={error} />}
      </Row>

      <TransactionForm
        show={showForm}
        transaction={selectedTransaction}
        onHide={() => setShowForm(false)}
        onSave={handleSaveTransaction}
      />
    </Container>
  );
}
