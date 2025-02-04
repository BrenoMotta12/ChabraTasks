import { List } from "./List"

export interface Space {
    id: string
    color?: string
    description?: string
    name?: string
    lists: List[]
}

