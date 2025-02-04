import { Priority } from "./Priority";
import { Status } from "./Status";
import { User } from "./User";

export interface Task{
    id: string;
    name: string;
    description?: string;
    dueDate?: Date;
    status: Status;
    priority?: Priority;
    responsibles?: User[];
    createdAt: Date;
    completedAt?: Date;
    listTaskId: string;
}

