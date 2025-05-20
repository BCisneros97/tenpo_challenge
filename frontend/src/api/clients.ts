import axios from "axios";
import type Client from "../models/client";

export const getClients = async (): Promise<Client[]> => {
  const { data } = await axios.get("http://localhost:8080/clients");
  return data;
};
