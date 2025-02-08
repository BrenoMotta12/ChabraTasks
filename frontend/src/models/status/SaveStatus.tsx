import { StatusType } from "./StatusType";

export interface SaveStatus{
    id?: string;
    description: string;
    color?: string;
    statusType: StatusType;
}