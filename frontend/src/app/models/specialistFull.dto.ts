export interface SpecialistFullDto {
  id: number;
  idUtilizator: number;
  specializareId: number;
  serviciuIds: number[];
  atestat: string;
  statusValidare: 'IN_PROCES' | 'VALIDAT' | 'RESPINS';
  descriere: string;
  soldAcumulat: string;      // ex: "0.00"
  idAdmin: number | null;    // cine l-a validat
  dataValidare: string | null; // ISO date, sau null dacă încă „in proces”
}
