import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import useAuth from '../../../hooks/useAuth';
import { Api, getHeadersAuthorization } from '../../../services/Api';
import { Task } from '../../../models/Task';

export default function BoardSubfragment() {
  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();
  const [tasks, setTasks] = useState<Task[]>([]);
  const [groupBy, setGroupBy] = useState<'status' | 'priority' | 'user' | 'dueDate' | null>('status');

  useEffect(() => {
    if (!id || !user?.token) return;
    fetchData(id);
  }, [id, user?.token]);

  const fetchData = async (listId: string) => {
    try {
      const response = await Api.get(`task/byList/${listId}`, getHeadersAuthorization(user?.token));
      setTasks(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const formatDueDate = (date: Date) => {
    // Formatar data no formato dd/MM/yyyy
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
  };

  const groupTasks = () => {
    return tasks.reduce((acc, task) => {
      let key = 'Outros';
      let color = 'bg-gray-300'; // Cor padrão caso não haja status ou prioridade

      if (groupBy === 'status') {
        key = task.status?.description || 'Sem Status';
        color = task.status?.color || 'bg-gray-300'; // Cor do status
      } 
      else if (groupBy === 'priority') {
        key = task.priority?.description || 'Sem Prioridade';
        color = task.priority?.color || 'bg-gray-300'; // Cor da prioridade
      } 
      else if (groupBy === 'user') {
        // Agrupar por combinação de responsáveis
        if (task.responsibles && task.responsibles.length > 0) {
          const responsibleNames = task.responsibles
            .map(responsible => responsible.name)
            .sort()
            .join(', '); // Combinar os nomes em uma string
          key = responsibleNames || 'Sem Responsável';
        } else {
          key = 'Sem Responsável';
        }
      } 
      else if (groupBy === 'dueDate') {
        // Agrupar por data de vencimento (formato dd/MM/yyyy)
        key = task.dueDate ? formatDueDate(new Date(task.dueDate)) : 'Sem Data de Vencimento';
      }

      if (!acc[key]) acc[key] = { tasks: [], color }; // Adiciona a cor ao grupo
      acc[key].tasks.push(task);
      return acc;
    }, {} as Record<string, { tasks: Task[], color: string }>); 
  };

  const sortedGroupedTasks = () => {
    const grouped = groupTasks();
    
    // Ordenar as chaves de acordo com a data de vencimento (ou a mais próxima)
    const sortedKeys = Object.keys(grouped).sort((a, b) => {
      const dateA = a === 'Sem Data de Vencimento' ? new Date() : new Date(a.split('/').reverse().join('-'));
      const dateB = b === 'Sem Data de Vencimento' ? new Date() : new Date(b.split('/').reverse().join('-'));
      
      // Ordenar de forma crescente (da data mais próxima para as vencidas)
      return dateA.getTime() - dateB.getTime();
    });

    // Reorganizar os itens com base na chave ordenada
    const sortedGrouped = sortedKeys.reduce((acc, key) => {
      acc[key] = grouped[key];
      return acc;
    }, {} as Record<string, { tasks: Task[], color: string }>);

    return sortedGrouped;
  };

  const groupedTasks = sortedGroupedTasks();

  return (
    <div className="flex flex-col h-full">
      {/* Filtros de agrupamento */}
      <div className='flex items-center gap-2 mb-2 rounded-xl border border-tertiary w-fit py-1 px-2 bg-secondary'>
        <h1>Agrupar por:</h1>
        <div className="flex gap-2 items-center">
          <button onClick={() => setGroupBy('status')} className={`px-3 py-1 rounded-xl border border-tertiary sm:w-auto ${groupBy === 'status' ? "bg-bg-selected" : "bg-secondary"}`}>
            Status
          </button>
          <button onClick={() => setGroupBy('priority')} className={`px-3 py-1 rounded-xl border border-tertiary sm:w-auto ${groupBy === 'priority' ? "bg-bg-selected" : "bg-secondary"}`}>
            Prioridade
          </button>
          <button onClick={() => setGroupBy('user')} className={`px-3 py-1 rounded-xl border border-tertiary sm:w-auto ${groupBy === 'user' ? "bg-bg-selected" : "bg-secondary"}`}>
            Responsável
          </button>
          <button onClick={() => setGroupBy('dueDate')} className={`px-3 py-1 rounded-xl border border-tertiary sm:w-auto ${groupBy === 'dueDate' ? "bg-bg-selected" : "bg-secondary"}`}>
            Data de Vencimento
          </button>
        </div>
      </div>

      {/* Container principal */}
      <div className="flex flex-1 w-full max-h-full overflow-x-auto overflow-y-hidden pb-11 gap-4">
        {Object.entries(groupedTasks).map(([group, { tasks, color }]) => (
          <div key={group} className="w-80">
            <div className='flex items-center'>
              <h2
                className={`w-fit mb-2 py-1 px-2 rounded-md border border-tertiary`}
                style={{ backgroundColor: color }}
              >
                {group}
              </h2>
              <h2 className='w-fit mb-2 py-1 px-4'>
                {tasks.length}
              </h2>
            </div>

            {/* Container das tarefas com rolagem limitada e responsiva */}
            <div className="max-h-full w-full overflow-y-auto p-2 border border-tertiary rounded-xl">
              {tasks.map((task) => (
                <div key={task.id} className="bg-white p-4 rounded-lg shadow-md mb-4">
                  <h2 className="text-lg font-semibold">{task.name}</h2>
                  <p className="text-gray-600">{task.description}</p>
                  <p className="text-gray-600">Due Date: {task.dueDate ? formatDueDate(new Date(task.dueDate)) : 'Sem Data de Vencimento'}</p>
                  <p className="text-gray-600">Status: {task.status?.description}</p>
                  <p className="text-gray-600">Priority: {task.priority?.description}</p>
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
