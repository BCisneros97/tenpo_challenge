import axios from "axios";
import type Transaction from "../models/transaction";

export const getTransactions = async (): Promise<Transaction[]> => {
  const { data } = await axios.get(import.meta.env.VITE_BACKEND_URL + "/transactions");
  return data;
};

export const createTransaction = async (formData: any) => {
  const { data } = await axios.post(
    import.meta.env.VITE_BACKEND_URL + "/transactions",
    formData
  );
  return data;
};

export const updateTransaction = async (id: number, formData: any) => {
  const { data } = await axios.put(
    import.meta.env.VITE_BACKEND_URL + `/transactions/${id}`,
    formData
  );
  return data;
};

export const deleteTransaction = async (id: number) => {
  const { data } = await axios.delete(
    import.meta.env.VITE_BACKEND_URL + `/transactions/${id}`
  );
  return data;
};
