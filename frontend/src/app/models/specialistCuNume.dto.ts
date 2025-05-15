import { ServiciuDto } from './serviciu.dto';

export interface SpecialistCuNumeDto {
  id: number;
  idUtilizator: number;
  nume: string,
  atestat: string | null;
  descriere: string | null;

    servicii: ServiciuDto[];    // ← adăugat
}
