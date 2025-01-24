import { User } from "./User";

export interface AuthContextModel {
    user: User | null;
    login: (data: User) => Promise<boolean>;
    signup: (data: User) => Promise<boolean>;
    logout: () => void;
  }