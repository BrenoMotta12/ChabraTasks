import { StatusType } from "./StatusType";

export interface Status{

    id: string;
    description: string;
    color: string;
    statusType: StatusType;
}