import { useEffect, useState } from "react";
import { Status } from "../../../models/status/Status";
import { StatusType } from "../../../models/status/StatusType";
import useAuth from "../../../hooks/useAuth";
import { Api, getHeadersAuthorization, getHeadersBodyAuthorization } from "../../../services/Api";
import { SaveStatus } from "../../../models/status/SaveStatus";

const statusTypeMap: Record<StatusType, string> = {
  NOT_STARTED: "Não iniciado",
  IN_PROGRESS: "Em progresso",
  COMPLETED: "Concluído",
};

export default function StatusFragment() {
  const [listStatus, setListStatus] = useState<Status[]>([]);
  const [selectedStatus, setSelectedStatus] = useState<Status | null>(null);
  const [newStatus, setNewStatus] = useState<Record<StatusType, { description: string; color: string }>>({} as any);
  const [isOpenDeleteModal, setIsOpenDeleteModal] = useState<boolean>(false);
  const { user } = useAuth();
  const statusOrder: StatusType[] = [StatusType.NOT_STARTED, StatusType.IN_PROGRESS, StatusType.COMPLETED];

  const fetchStatus = async () => {
    try {
      const response = await Api.get<Status[]>("/status", getHeadersAuthorization(user?.token));
      setListStatus(response.data);
    } catch (error) {
      console.error("Erro ao buscar status:", error);
    }
  };

  useEffect(() => {
    fetchStatus();
  }, [user?.id]);

  const groupedStatus = listStatus.reduce((acc, status) => {
    if (!acc[status.statusType]) {
      acc[status.statusType] = [];
    }
    acc[status.statusType].push(status);
    return acc;
  }, {} as Record<StatusType, Status[]>);
  
  // Ordenando os status de acordo com statusOrder
  const orderedGroupedStatus = statusOrder.map((statusType) => ({
    type: statusType,
    statuses: groupedStatus[statusType] || [], // Se não existir, retorna um array vazio
  }));


  // Função para abrir o modal de edição
  const openEditModal = (status: Status) => {
    setSelectedStatus(status);
  };

  // Fechar modal de edição
  const closeEditModal = () => {
    setSelectedStatus(null);
  };

  // Excluir status
  const handleDeleteStatus = async () => {
    if (!selectedStatus) return;

    try {
      await Api.delete(`/status/${selectedStatus.id}`, getHeadersAuthorization(user?.token));
      setIsOpenDeleteModal(false);
      closeEditModal()
      fetchStatus(); // Atualiza a lista após a exclusão
    } catch (error) {
      console.error("Erro ao excluir status:", error);
    }
  };

  const handleAddStatus = async (statusType: StatusType) => {
    if (!newStatus[statusType]?.description) return;
    const statusData: SaveStatus = {
      description: newStatus[statusType].description,
      statusType,
      color: newStatus[statusType].color,
    };
    try {
      await Api.post("/status", statusData, getHeadersBodyAuthorization(user?.token));
      setNewStatus({ ...newStatus, [statusType]: { description: "", color: "#000000" } });
      fetchStatus();
    } catch (error) {
      console.error("Erro ao adicionar status:", error);
    }
  };

  const handleUpdateStatus = async () => {
    if (!user?.token || !selectedStatus) return;
    const statusData: SaveStatus = {
      id: selectedStatus.id,
      description: selectedStatus.description,
      statusType: selectedStatus.statusType,
      color: selectedStatus.color,
    };
    try {
      await Api.put(`/status`, statusData, getHeadersBodyAuthorization(user?.token));
      closeEditModal();
      fetchStatus();
    } catch (error) {
      console.error("Erro ao atualizar status:", error);
    }
  };


  return (
    <div className="flex flex-col gap-4 h-[95%] overflow-y-auto">
      {orderedGroupedStatus.map(({ type, statuses }) => (
        <div key={type} className="border border-tertiary p-4 rounded-lg shadow-md">
          <h2 className="text-lg font-semibold">{statusTypeMap[type] || type}</h2>
          <ul className="mt-2 flex flex-col gap-2">
            {statuses.map((status) => (
              <li
                key={status.id}
                className="flex items-center gap-2 p-2 rounded-md cursor-pointer bg-gray-100 hover:bg-gray-200 transition-colors"
                onClick={() => openEditModal(status)}
                style={{ borderLeft: `4px solid ${status.color}` }}
              >
                <span>{status.description}</span>
              </li>
            ))}
          </ul>

          {/* Formulário para adicionar novo status */}
          <form className="mt-4 flex gap-2 items-center" onSubmit={(e) => e.preventDefault()}>
            <input
              type="color"
              value={newStatus[type]?.color || "#000000"}

              onChange={(e) =>
                setNewStatus({
                  ...newStatus,
                  [type]: {
                    ...newStatus[type],
                    color: e.target.value,
                  },
                })
              }
              className="h-7 w-6"
            />
            <input
              type="text"
              value={newStatus[type]?.description || ""}
              onChange={(e) =>
                setNewStatus({
                  ...newStatus,
                  [type]: {
                    ...newStatus[type],
                    description: e.target.value,
                  },
                })
              }
              placeholder="Novo status..."
              className="border py-1 px-2 flex-1 rounded-md"
            />
            <button
              onClick={() => handleAddStatus(type)}
              className="bg-blue-500 text-white px-2 py-1 rounded-md"
            >
              Adicionar
            </button>
          </form>
        </div>
      ))}

      {/* Modal de Edição */}
      {selectedStatus && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-black opacity-50"></div>
          <div className="relative bg-white rounded-lg shadow-lg p-4 flex flex-col items-center">
            <h3 className="text-lg font-bold">Editar Status</h3>
            <input
              type="text"
              value={selectedStatus.description}
              onChange={(e) => setSelectedStatus({ ...selectedStatus, description: e.target.value })}
              className="border p-2 rounded-md w-full mt-2"
            />
            <input
              type="color"
              value={selectedStatus.color}
              onChange={(e) => setSelectedStatus({ ...selectedStatus, color: e.target.value })}
              className="mt-2"
            />
            <div className="flex justify-end mt-4 gap-2">
              <button onClick={closeEditModal} className="px-4 py-2 border rounded-md">Cancelar</button>
              <button onClick={() => setIsOpenDeleteModal(true)} className="px-4 py-2 bg-red-500 text-white rounded-md">Excluir</button>
              <button onClick={handleUpdateStatus} className="bg-button-confim text-white px-4 py-2 rounded-md">Salvar</button>
            </div>
          </div>
        </div>
      )}

      {/* Modal de Exclusão */}
      {isOpenDeleteModal && (
        <div className="fixed inset-0 flex items-center justify-center z-50">
          <div className="absolute inset-0 bg-black opacity-50"></div>
          <div className="relative bg-white rounded-lg shadow-lg p-4 flex flex-col items-center">
            <h3 className="text-lg font-bold">Excluir Status</h3>
            <p className="mt-2 text-sm">Tem certeza que deseja excluir o status <strong>{selectedStatus?.description}</strong>?</p>
            <div className="flex justify-end mt-4 gap-2">
              <button onClick={() => setIsOpenDeleteModal(false)} className="px-4 py-2 border  rounded-md">Cancelar</button>
              <button onClick={handleDeleteStatus} className="bg-red-500 text-white px-4 py-2 rounded-md">Excluir</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}