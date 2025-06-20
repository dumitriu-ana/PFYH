export interface ComandaDto {
  id?: number;
  idClient?: number;
  idSpecialist?: number;
  idServiciu?: number;
  dataCreare?: string;
  status?: string;
  pret?: number;
  mesajClient?: string;
  numeFisierClient?: string;
  fisierClient?: any;
  dataMaximaLivrare?: string;
  mesajSpecialist?: string;
  numeFisierSpecialist?: string;
  fisierSpecialist?: any;

  titluServiciu?: string;
  numeSpecialist?: string;
  numeClient?: string;
}
