import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { ServiciuDto } from '../../../models/serviciu.dto';
import { SpecialistCuNumeDto } from '../../../models/specialistCuNume.dto';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { ComenziService } from '../../../services/comenzi.service';
import { ComandaDto } from '../../../models/comanda.dto';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-comanda-dialog',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatDialogModule, MatButtonModule],
  templateUrl: './comanda-dialog.component.html',
  styleUrls: ['./comanda-dialog.component.css']
})
export class ComandaDialogComponent {
  form: FormGroup;
  fisierSelectat?: File;

constructor(
  private dialogRef: MatDialogRef<ComandaDialogComponent>,
  private fb: FormBuilder,
  private comenziService: ComenziService,
  private authService: AuthService, // presupunem că ai un serviciu pentru user
  @Inject(MAT_DIALOG_DATA) public data: { serviciu: ServiciuDto, specialist: SpecialistCuNumeDto }
) {
  this.form = this.fb.group({
    mesaj: [''],
    fisier: [null]
  });
}

onFisierSelectat(event: any) {
  const file = event.target.files[0];
  if (file && file.type === 'application/pdf') {
    if (file.size > 10000 * 1024 * 1024) { //  bytes
      alert('Fișierul este prea mare. Dimensiunea maximă permisă este 10MB.');
      return;
    }
    this.fisierSelectat = file;
  }
}


trimiteComanda() {
  const currentUser = this.authService.getCurrentUser();
  if (!currentUser) {
    alert('Trebuie să fii logat pentru a plasa o comandă.');
    return;
  }

  const comanda: ComandaDto = {
    idClient: currentUser.id,
    idSpecialist: this.data.specialist.id,
    idServiciu: this.data.serviciu.id,
    pret: this.data.serviciu.pret,
    mesajClient: this.form.value.mesaj
  };

  const formData = new FormData();
  formData.append('comanda', new Blob([JSON.stringify(comanda)], { type: 'application/json' }));

  if (this.fisierSelectat) {
    formData.append('fisier', this.fisierSelectat, this.fisierSelectat.name);
  }

  this.comenziService.creeazaComandaCuFisier(formData).subscribe({
    next: (response) => {
      console.log('Comandă cu fișier trimisă:', response);
      this.dialogRef.close(response);

    },
    error: (err) => {
      console.error('Eroare la trimiterea comenzii:', err);
    }
  });
}


  inchide() {
    this.dialogRef.close();
  }
}
