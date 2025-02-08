import { Priority } from "../priority/Priority";
import { Status } from "../status/Status";
import { Tag } from "../tag/Tag";
import { User } from "../user/User";

export interface Task{
    id: string;
    name: string;
    description?: string;
    dueDate?: Date;
    status: Status;
    priority?: Priority;
    responsibles?: User[];
    tags?: Tag[]
    createdAt: Date;
    completedAt?: Date;
    listTaskId: string;
}
