import { UtilizatorDto } from './utilizator.dto';

export interface AuthResponse {
  token: string;
  user: UtilizatorDto;
}
