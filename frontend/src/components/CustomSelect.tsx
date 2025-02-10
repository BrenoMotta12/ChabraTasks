import { useState, useEffect, useRef } from "react";
import { X } from "lucide-react";
import { StatusType } from "../models/status/StatusType";

export interface Option {
  id: string;
  description: string;
  color?: string;
  statusType?: StatusType; // Apenas para Status
}

interface CustomSelectProps {
  options: Option[];
  selected: string | string[];
  setSelected: (value: string | string[]) => void;
  multiple?: boolean;
  groupByStatus?: boolean; // Se true, agrupa por status
}

export default function CustomSelect({ options, selected, setSelected, multiple = false, groupByStatus = false }: CustomSelectProps) {
  const [isOpen, setIsOpen] = useState(false);
  const selectRef = useRef<HTMLDivElement>(null);

  const selectedItems = Array.isArray(selected) ? selected : [selected];
  const selectedOptions = options.filter((opt) => selectedItems.includes(opt.id));

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (selectRef.current && !selectRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleSelect = (id: string) => {
    if (multiple) {
      if (selectedItems.includes(id)) {
        setSelected(selectedItems.filter((item) => item !== id));
      } else {
        setSelected([...selectedItems, id]);
      }
    } else {
      setSelected(id);
      setIsOpen(false);
    }
  };

  const handleRemove = (id: string) => {
    setSelected(selectedItems.filter((item) => item !== id));
  };

  // Se `groupByStatus` for true, agrupar responsáveis por `statusType`
  const groupedByStatus = groupByStatus
    ? selectedOptions.reduce((acc, option) => {
        if (!option.statusType) return acc; // Garante que só agrupa se tiver `statusType`
        if (!acc[option.statusType]) {
          acc[option.statusType] = [];
        }
        acc[option.statusType].push(option);
        return acc;
      }, {} as Record<StatusType, Option[]>)
    : null;

  const statusLabels: Record<StatusType, string> = {
    [StatusType.NOT_STARTED]: "Não Iniciado",
    [StatusType.IN_PROGRESS]: "Em Andamento",
    [StatusType.COMPLETED]: "Concluído",
  };

  return (
    <div className="relative w-50" ref={selectRef}>
      {/* Botão do dropdown */}
      <div
        className="border border-tertiary rounded-md px-2 py-1 cursor-pointer flex items-center flex-wrap gap-1 min-h-[2.5rem] bg-white"
        onClick={() => setIsOpen(!isOpen)}
      >
        {selectedOptions.length > 0 ? (
          <div className="flex flex-wrap gap-1">
            {groupByStatus && groupedByStatus
              ? Object.entries(groupedByStatus).map(([status, items]) => (
                  <div key={status} className="flex items-center gap-2">
                    <span className="text-xs font-semibold text-gray-500">{statusLabels[status as StatusType]}:</span>
                    {items.map((opt) => (
                      <span
                        key={opt.id}
                        className="flex items-center gap-1 text-sm px-2 py-1 rounded-md"
                        style={{ backgroundColor: opt.color || "#ddd", color: "#fff" }}
                      >
                        {opt.description}
                        <X size={14} className="cursor-pointer hover:text-red-500" onClick={(e) => { 
                          e.stopPropagation(); 
                          handleRemove(opt.id); 
                        }} />
                      </span>
                    ))}
                  </div>
                ))
              : selectedOptions.map((opt) => (
                  <span
                    key={opt.id}
                    className="flex items-center gap-1 text-sm px-2 py-1 rounded-md"
                    style={{ backgroundColor: opt.color || "#AD46FF", color: "#fff" }}
                  >
                    {opt.description}
                    <X size={14} className="cursor-pointer hover:text-red-500" onClick={(e) => { 
                      e.stopPropagation(); 
                      handleRemove(opt.id); 
                    }} />
                  </span>
                ))}
          </div>
        ) : (
          <span className="text-gray-400">Selecione...</span>
        )}
      </div>

      {/* Lista de opções */}
      {isOpen && (
        <div className="absolute mt-1 w-full bg-white border border-gray-300 rounded-md shadow-md z-10 max-h-40 overflow-y-auto">
          {groupByStatus
            ? Object.values(StatusType).map((status) => (
                <div key={status}>
                  <div className="px-3 py-1 bg-gray-100 text-xs font-semibold text-gray-600">{statusLabels[status]}</div>
                  {options
                    .filter((opt) => opt.statusType === status)
                    .map((opt) => {
                      const isSelected = selectedItems.includes(opt.id);
                      return (
                        <div
                          key={opt.id}
                          className={`cursor-pointer px-3 py-2 flex items-center gap-2 hover:bg-gray-100 ${isSelected ? "bg-gray-200" : ""}`}
                          onClick={() => handleSelect(opt.id)}
                        >
                          {multiple && <input type="checkbox" checked={isSelected} readOnly className="mr-1" />}
                          {opt.color && <span className="w-3 h-3 rounded-full flex-shrink-0" style={{ backgroundColor: opt.color }}></span>}
                          <span className="truncate">{opt.description}</span>
                        </div>
                      );
                    })}
                </div>
              ))
            : options.map((opt) => {
                const isSelected = selectedItems.includes(opt.id);
                return (
                  <div
                    key={opt.id}
                    className={`cursor-pointer px-3 py-2 flex items-center gap-2 hover:bg-gray-100 ${isSelected ? "bg-gray-200" : ""}`}
                    onClick={() => handleSelect(opt.id)}
                  >
                    {multiple && <input type="checkbox" checked={isSelected} readOnly className="mr-1" />}
                    {opt.color && <span className="w-3 h-3 rounded-full flex-shrink-0" style={{ backgroundColor: opt.color }}></span>}
                    <span className="truncate">{opt.description}</span>
                  </div>
                );
              })}
        </div>
      )}
    </div>
  );
}
