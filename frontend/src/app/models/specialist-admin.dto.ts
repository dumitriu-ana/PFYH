import { ServiciuDto } from './serviciu.dto';

export interface SpecialistAdminDto {
  id: number;
  idUtilizator: number;
  numeUtilizator: string;
  emailUtilizator: string;
  tipUtilizator: string;

  specializareId: number;
  denumireSpecializare: string;

  servicii: ServiciuDto[];
  atestat: string;
  descriere: string;
  statusValidare: string;
  soldAcumulat: string;
  idAdmin: number | null;
  dataValidare: string | null;
}
