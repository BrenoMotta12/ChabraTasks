import { List } from "../list/List"

export interface Space {
    id: string
    color?: string
    description?: string
    name: string
    lists: List[]
}

