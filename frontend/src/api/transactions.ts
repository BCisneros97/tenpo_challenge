import axios from "axios";
import type Transaction from "../models/transaction";

export const getTransactions = async (): Promise<Transaction[]> => {
  const { data } = await axios.get("http://localhost:8080/transactions");
  return data;
};

export const createTransaction = async (formData: any) => {
  const { data } = await axios.post(
    "http://localhost:8080/transactions",
    formData
  );
  return data;
};

export const updateTransaction = async (id: number, formData: any) => {
  const { data } = await axios.put(
    `http://localhost:8080/transactions/${id}`,
    formData
  );
  return data;
}

export const deleteTransaction = async (id: number) => {
  const { data } = await axios.delete(
    `http://localhost:8080/transactions/${id}`
  );
  return data;
}
