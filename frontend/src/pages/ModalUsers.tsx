import React, { useEffect, useState } from 'react'
import { User } from '../models/user/User';
import useAuth from '../hooks/useAuth';
import { SaveUser } from '../models/user/SaveUser';
import { Api, getHeadersAuthorization, getHeadersBodyAuthorization } from '../services/Api';
import { AxiosError } from 'axios';
import closeIcon from '../assets/close.svg'
import InputModal from '../components/InputModal';

interface ModalUsersProps {
    isModalOpen: boolean;
    setIsModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  }
  
  export default function ModalUsers({ isModalOpen, setIsModalOpen }: ModalUsersProps) {
  const [users, setUsers] = useState<User[]>([]);
  const [userName, setUserName] = useState<string>();
  const [userEmail, setUserEmail] = useState<string>();
  const [userPassword, setUserPassword] = useState<string>();
  const [userRole, setUserRole] = useState("USER");
  const [selectedUserId, setSelectedUserId] = useState<string | null>(null);
  const [isLoadingUser, setIsLoadingUser] = useState(false);
  const [isLoadingUsers, setIsLoadingUsers] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [userDeleted, setUserDeleted] = useState(false);
  const { user, logout } = useAuth();

  async function getUsers() {
    setIsLoadingUsers(true);
    try {
      const response = await Api.get("/users", getHeadersAuthorization(user?.token));
      setUsers(response.data);
    } catch (e) {
      if (e instanceof AxiosError && e.response?.status === 403) logout();
      console.log(e);
    } finally {
      setIsLoadingUsers(false);
    }
  }

  useEffect(() => {
    if (!isModalOpen) return;
    getUsers();
  }, [isModalOpen]);

  useEffect(() => {
    if (!isModalOpen) return;
    if (!selectedUserId) {

      // limpa as informações do antigo usuário
      setUserEmail("");
      setUserName("");
      setUserPassword("");
      setUserDeleted(false);
      setUserRole("USER");
    }
    if (!user?.token) return;

    const getUserById = async () => {
      if(!selectedUserId) return
      setIsLoadingUser(true)
  
      // limpa as informações do antigo usuário
      setUserEmail("");
      setUserName("");
      setUserPassword("");
      setUserDeleted(false);
      setUserRole("USER");
      try {
        const response = await Api.get(`/users/${selectedUserId}`, getHeadersAuthorization(user.token));
        const userFetched: User = response.data;
        setUserName(userFetched.name);
        setUserEmail(userFetched.email);
        setUserRole(userFetched.role || "USER");
        setUserDeleted(userFetched.deletedAt ? true : false);
      } catch (e) {
        if (e instanceof AxiosError && e.response?.status === 403) logout();
        console.log(e);
      } finally {
       setIsLoadingUser(false)  
      }
      
    };

    getUserById();
  }, [selectedUserId]);

    async function handleSubmitUser(event: React.FormEvent) {
        event.preventDefault();
        if (!user?.token) return;
    
        setIsSubmitting(true);
        try {
          if (!selectedUserId) {
            await Api.post("users/signUp", { name: userName, email: userEmail, password: userPassword, role: userRole }, getHeadersBodyAuthorization(user?.token));
          } else {
            const data: SaveUser = { id: selectedUserId, name: userName, email: userEmail, password: userPassword, role: userRole, deleted: userDeleted };
            await Api.put("/users", data, getHeadersBodyAuthorization(user?.token));
          }
          getUsers();
        } catch (e) {
          if (e instanceof AxiosError && e.response?.status === 403) logout();
          console.log(e);
        } finally {
          setIsSubmitting(false);
        }
      }
    return(
        <div className="fixed inset-0 flex items-center justify-center z-50">
              <div className="absolute inset-0 bg-black opacity-50"></div>
    
              {/* Conteúdo do Modal */}
              <div className="relative bg-white rounded-lg shadow-lg py-8 px-20 w-2/4 flex flex-col items-center">
                <img src={closeIcon} className='absolute top-4 right-4 h-8 cursor-pointer' onClick={() => setIsModalOpen(false)} />
    
                <h2 className="text-3xl font-semibold pb-6">Cadastro de usuários</h2>
    
                <div className='flex justify-between gap-8 w-full'>
                  <div className='border border-tertiary rounded-2xl py-2'>
    
                    <div className='border-b-1 pb-1 border-tertiary px-2' onClick={() => (setSelectedUserId(null))}>
                      <p className={`px-2 text-center ${selectedUserId == null ? "bg-gray-200 rounded-2xl" : ""}`}>Novo usuário</p>
                    </div>
    
                    {isLoadingUsers ? (
                      <p className="text-center p-2">Carregando usuários...</p>
                    ) : (
                      <div className='pt-2 px-2'>
                        {users.map((user) => (
                          <div
                            key={user.id}
                            className={`border-b-1 flex flex-col items-center cursor-pointer`}
                            onClick={() => (setSelectedUserId(user.id || null))}
                          >
                            <p className={`w-full text-center ${selectedUserId == user.id ? "bg-gray-200 rounded-2xl" : "" } ${user.deletedAt ? 'text-[#7F7F7F]' : ''}`}>
                              {user.name}
                            </p>
                          </div>
                        ))}
                      </div>
                    )}
                  </div>
    
                  <form className='flex flex-col flex-1 border border-tertiary rounded-2xl p-4 gap-4' onSubmit={handleSubmitUser}>
                    <InputModal value={userName} onChange={(e) => (setUserName(e.target.value))} required={true} label='Nome' className='w-5/6' disabled={isLoadingUser} placeholder={isLoadingUser ? "Carregando nome..." : ''}/>
                    <InputModal value={userEmail} onChange={(e) => (setUserEmail(e.target.value))} required={true} label='E-mail' className='w-5/6' disabled={isLoadingUser} placeholder={isLoadingUser ? "Carregando e-mail..." : ''}/>
                    <InputModal value={userPassword} onChange={(e) => (setUserPassword(e.target.value))} required={!selectedUserId} label='Senha' className='w-5/6' disabled={isLoadingUser} />
    
                    {/* DIV de seleção de role do usuário*/}
                    <div className='flex items-center justify-between'>
                      <label>Função</label>
                      <select value={userRole} onChange={(e) => setUserRole(e.target.value)} className="block w-5/6 py-1 px-2 border border-gray-300 rounded-xl">
                        <option value="ADMIN">ADMIN</option>
                        <option value="MODERATOR">MODERATOR</option>
                        <option value="USER">USER</option>
                      </select>
                    </div>
                      
                    <div className='flex items-center gap-3'>
                      <label>Desativar usuário</label>
                      <input 
                        type="checkbox" 
                        className='h-4 w-4' 
                        checked={userDeleted || false} 
                        onChange={(e) => setUserDeleted(e.target.checked)} 
                      />
                    </div>
    
                    <div className='flex justify-center gap-2'>
                      <button type='submit' className={`bg-button-confim py-1 w-20 rounded-2xl text-white ${isSubmitting ? "opacity-50 cursor-not-allowed" : ""}`} disabled={isSubmitting}>
                        {isSubmitting ? "Salvando..." : "Salvar"}
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
    )
}
