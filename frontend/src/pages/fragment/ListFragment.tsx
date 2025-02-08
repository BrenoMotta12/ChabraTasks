import { useEffect, useState } from 'react'
import Fragment from '../../components/Fragment'
import { Outlet, useParams } from 'react-router-dom'
import { Space } from '../../models/space/Space'
import useAuth from '../../hooks/useAuth'
import { List } from '../../models/list/List'
import { Api, getHeadersAuthorization } from '../../services/Api'
import plusIcon from '../../assets/plus.svg'
import FragmentNavBar from '../../components/FragmentNavBar'
import ModalTask from './modalTask/ModalTask'

export default function ListFragment() {

    const [space, setSpace] = useState<Space>()
    const [list, setList] = useState<List>()
    const { id } = useParams<{ id: string }>()
    const [ isOpenModalNewTask, setIsOpenModalNewTask] = useState<boolean>(false)
    const { user } = useAuth()
   
    
    useEffect(() => {
        if (user?.token) {
            fetchList();
        }
    }, [user?.token, id]);

    useEffect(() => {
        if (list?.spaceId) {
            fetchSpace();
        }
    }, [list]);

    const fetchList = async () => {
        try {
            const response = await Api.get(`/list/${id}`, getHeadersAuthorization(user?.token));
            setList(response.data);
        } catch (error) {
            console.error("Erro ao buscar lista:", error);
        }
    };

    const fetchSpace = async () => {
        try {
            const response = await Api.get(`/space/${list?.spaceId}`, getHeadersAuthorization(user?.token));
            setSpace(response.data);
        } catch (error) {
            console.error("Erro ao buscar espaço:", error);
        }
    };

    return (
        <Fragment
            Icon={
                <h1 className="bg-[#02A85C] text-white font-medium text-2xl rounded-xl h-8 w-8 flex items-center justify-center">
                    {space?.name?.charAt(0).toUpperCase()}
                </h1>
            }
            title={`${space?.name} > ${list?.name}`}
        >
            
            <div className='flex border-b-1 border-tertiary items-center justify-between'>
                
                <FragmentNavBar
                    id={id}
                />

                <div className='flex me-4 gap-2 items-center bg-secondary border border-tertiary px-2 py-1 rounded-2xl' onClick={()=>setIsOpenModalNewTask(true)}>
                    <img src={plusIcon}
                         />
                    <h2>Nova Tarefa</h2>
                </div>
            </div>
           
            <div className="overflow-x-auto overflow-y-hidden p-2 w-full h-full">
                <Outlet />
                {isOpenModalNewTask && (
                    <ModalTask setIsModalOpen={setIsOpenModalNewTask}/>
                )}
            </div>
            

        </Fragment>
    )
}
