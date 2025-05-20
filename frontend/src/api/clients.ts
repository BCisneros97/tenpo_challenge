import axios from "axios";
import type Client from "../models/client";

export const getClients = async (): Promise<Client[]> => {
  const { data } = await axios.get(import.meta.env.VITE_BACKEND_URL + "/clients");
  return data;
};
