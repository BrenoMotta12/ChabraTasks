import React, { useEffect, useState } from 'react';
import { Status } from '../../../models/status/Status';
import { User } from '../../../models/user/User';
import { Tag } from '../../../models/tag/Tag';
import { Priority } from '../../../models/priority/Priority';
import { useParams } from 'react-router-dom';
import useAuth from '../../../hooks/useAuth';
import { Api, getHeadersAuthorization } from '../../../services/Api';
import CustomSelect from '../../../components/CustomSelect';

export default function TaskFragment() {
  const [nameNewTask, setNameNewTask] = useState<string>('');
  const [descriptionNewTask, setDescriptionNewTask] = useState<string>('');
  const [dueDateNewTask, setDueDateNewTask] = useState<string>('');
  const [statusNewTask, setStatusNewTask] = useState<string>('');
  const [responsiblesNewTask, setResponsiblesNewTask] = useState<string[]>([]);
  const [priorityNewTask, setPriorityNewTask] = useState<string>('');
  const [tagsNewTask, setTagsNewTask] = useState<string[]>([]);

  const [statusList, setStatusList] = useState<Status[]>([]);
  const [userList, setUserList] = useState<User[]>([]);
  const [priorityList, setPriorityList] = useState<Priority[]>([]);
  const [tagList, setTagList] = useState<Tag[]>([]);

  const { id } = useParams<{ id: string }>();
  const { user } = useAuth();

  useEffect(() => {
    fetchData();
  }, [user?.token, id]);

  const fetchData = async () => {
    try {
      const [statusRes, usersRes, priorityRes, tagsRes] = await Promise.all([
        Api.get<Status[]>('/status', getHeadersAuthorization(user?.token)),
        Api.get<User[]>('/users', getHeadersAuthorization(user?.token)),
        Api.get<Priority[]>('/priority', getHeadersAuthorization(user?.token)),
        Api.get<Tag[]>('/tag', getHeadersAuthorization(user?.token)),
      ]);
      setStatusList(statusRes.data);
      setUserList(usersRes.data);
      setPriorityList(priorityRes.data);
      setTagList(tagsRes.data);
    } catch (error) {
      console.error('Erro ao buscar lista:', error);
    }
  };

  return (
    <main className='flex gap-1 h-full w-300'>
      <form className='flex flex-col items-center flex-1 px-10 gap-10'>
        <input
          type="text"
          placeholder='Nome da tarefa'
          value={nameNewTask}
          onChange={(e) => setNameNewTask(e.target.value)}
          className='px-2 py-1 rounded-md border border-tertiary w-full'
        />

        

        <div className=' flex gap-2 rounded-md border border-tertiary p-2 bg-secondary'>
          <div className='flex flex-col gap-2 w-full'>

            <div className='flex items-center gap-2 justify-between'>
              <p>Data de vencimento</p>
            <input
              type="date"
              placeholder='Data de Vencimento'
              value={dueDateNewTask}
              onChange={(e) => setDueDateNewTask(e.target.value)}
              className='px-2 py-1 rounded-md border border-tertiary '
            />
            </div>
            <div className='flex items-center gap-2 w-full justify-between'>
              <p>Status</p>
              <CustomSelect
                options={statusList}
                selected={statusNewTask || []}
                setSelected={setStatusNewTask}
                groupByStatus
              />
            </div>

            <div className='flex items-center gap-2 w-full justify-between'>
              <p>Prioridade</p>
              <CustomSelect
                options={priorityList}
                selected={priorityNewTask || []}
                setSelected={setPriorityNewTask}
              />
            </div>

            <div className='flex items-center gap-2 w-full justify-between'>
              <p>Tags</p>
              <CustomSelect
                options={tagList}
                selected={tagsNewTask || []}
                setSelected={setTagsNewTask}
                multiple
              />
            </div>

            <div className='flex items-center gap-2 w-full justify-between'>
              <p>Responsáveis</p>
              <CustomSelect
                options={userList.map(user => ({ id: user.id, description: user.name }))}
                selected={responsiblesNewTask || []}
                setSelected={setResponsiblesNewTask}
                multiple
              />
            </div>
          </div>
        </div>
      </form>

      {/* Tela de Atividades */}
      <div className='flex flex-col min-h-full bg-secondary border border-tertiary rounded-md'>
        <h1 className='w-full bg-primary border-b border-tertiary p-2'>Atividade</h1>
        <div className='h-full p-2 flex flex-col'>
          <div className='flex-1'></div>
          <div className='border border-tertiary rounded-xl p-2 flex gap-2 text-[0.9rem] bg-primary'>
            <input
              type="text"
              placeholder='Escreva um comentário...'
              className='flex-1 px-2'
            />
            <button className='bg-purple-500 text-white py-1 px-2 rounded-md'>
              Enviar
            </button>
          </div>
        </div>
      </div>
    </main>
  );
}
