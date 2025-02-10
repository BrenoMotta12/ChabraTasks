import React, { useState } from 'react'
import closeIcon from '../../../assets/close.svg'
import TaskFragment from './TaskFragment';
import StatusFragment from './StatusFragment';
import TagFragment from './TagFragment';
import useAuth from '../../../hooks/useAuth';

interface ModalNewTaskProps {
    setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
    taskId?: string;
}

export default function ModalTask({ setIsModalOpen } : ModalNewTaskProps) {
  const [fragmentModal, setFragmentModal] = useState<"TASK" | "STATUS" | "TAG">("TASK")
  const { user } = useAuth()
  const fragmentComponents = {
    TASK: <TaskFragment />,
    STATUS: <StatusFragment />,
    TAG: <TagFragment />,
  };


  return (
    <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="absolute inset-0 bg-black opacity-50"></div>
        <div className="relative bg-white rounded-lg shadow-lg pt-4 min-w-2/6 h-3/4 flex flex-col items-center">

          <img src={closeIcon} className=' absolute top-2 right-2' onClick={()=>setIsModalOpen(false)} />
          {/* Navegação entre as abas */}
          <div className="w-full flex justify-between px-4 text-center border-b border-tertiary">
            {["TASK", ...(user?.role === "ADMIN" ? ["STATUS", "TAG"] : [])].map((tab) => (
              <h1
                key={tab}
                className={`w-1/4 h-full cursor-pointer ${fragmentModal === tab ? "border-b border-black" : ""}`}
                onClick={() => setFragmentModal(tab as "TASK" | "STATUS" | "TAG")}
              >
                {tab === "TASK" ? "Tarefa" : tab === "STATUS" ? "Status" : "Tag"}
              </h1>
            ))}
          </div>

          <div className="w-full h-full p-4">{fragmentComponents[fragmentModal]}</div>
        </div>

    </div>

  )
}
