export interface SpecialistFullDto {
  id: number;
  idUtilizator: number;
  specializareId: number;
  serviciuIds: number[];
  atestat: string;
  statusValidare: 'IN_PROCES' | 'VALIDAT' | 'RESPINS';
  descriere: string;
  soldAcumulat: string;
  idAdmin: number | null;
  dataValidare: string | null;
}
