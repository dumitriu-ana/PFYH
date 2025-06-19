export interface ComandaDto {
  id?: number;
  idClient: number;
  idSpecialist: number;
  idServiciu: number;
  dataCreare?: string;
  status?: string;
  pret: number;
  mesajClient: string;

  // PDF   client
  fisierClient?: string;
  numeFisierClient?: string;

  dataMaximaLivrare?: string;

  // rasp specialistului
  mesajSpecialist?: string;
  fisierSpecialist?: string;
  numeFisierSpecialist?: string;
}
