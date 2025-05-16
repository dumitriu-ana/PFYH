export interface UtilizatorDto {
  id: number;
  idFirebase: string;
  dataInreg: string;
  nume: string;
  email: string;
  parola: string | null;
  tipUtilizator: 'admin' | 'client' | 'specialist';
  sold: number;
}

