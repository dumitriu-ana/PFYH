export interface ComandaDto {
  id?: number;
  idClient: number;
  idSpecialist: number;
  idServiciu: number;
  dataCreare?: string; // poate fi null pentru POST
  status?: string;
  pret: number;
  mesajClient: string;
  fisierClient?: string; // sau base64 dacă decizi să encodezi PDF-ul
  dataMaximaLivrare?: string;
  mesajSpecialist?: string;
  fisierSpecialist?: string;
}
