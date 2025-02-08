
export interface SaveTask{
    name: string;
    description?: string;
    dueDate?: Date;
    statusId: string;
    priorityId?: string;
    responsibles?: string[];
    tags?: string[]
    listTaskId: string;
}

