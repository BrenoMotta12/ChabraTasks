import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import useAuth from '../../../hooks/useAuth'
import { Api, getHeadersAuthorization } from '../../../services/Api'
import { Task } from '../../../models/Task'

export default function BoardSubfragment() {

  const { id } = useParams<{ id: string }>()
  const { user } = useAuth()
  const [tasks, setTasks] = useState<Task[]>([])

  useEffect(() => {
      if(!id) return
      if(!user?.token) return

      fetchData(id)

  }, [id, user?.token])


  const fetchData = async(listId: string) => {
    try {
      console.log(listId)
      const response = await Api.get(`task/byList/${listId}`, getHeadersAuthorization(user?.token))

      console.log(response.data)
      setTasks(response.data)
    } catch (error) {
      console.log(error)
    }
  }
  

  return (
    
      
      <div>
        {tasks.map((task: Task) => (
          <div key={task.id} className="bg-white p-4 rounded-lg shadow-md mb-4">
            <h2 className="text-lg font-semibold">{task.name}</h2>
            <p className="text-gray-600">{task.description}</p>
            <p className="text-gray-600">Due Date: {task.dueDate?.toString()}</p>
            <p className="text-gray-600">Status: {task.status.description}</p>
            <p className="text-gray-600">Priority: {task.priority?.description}</p>
            <p className="text-gray-600">Created At: {task.createdAt?.toString()}</p>
            <p className="text-gray-600">Completed At: {task.completedAt?.toString()}</p>
            <p className="text-gray-600">List Id: {task.listTaskId}</p>
          </div>
        ))}
      </div>
    
  );
  
}
