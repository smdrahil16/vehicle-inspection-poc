resource "aws_s3_bucket" "car_images_bucket" {
  bucket = var.bucket_name

  tags = {
    Name        = "Car Inspection Images Bucket"
    Environment = "POC"
  }
}

resource "aws_s3_bucket_public_access_block" "block_public_access" {
  bucket = aws_s3_bucket.car_images_bucket.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

resource "aws_s3_bucket_policy" "public_read_policy" {
  bucket = aws_s3_bucket.car_images_bucket.id

  depends_on = [
    aws_s3_bucket_public_access_block.block_public_access
  ]

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Sid    = "PublicReadGetObject"
        Effect = "Allow"
        Principal = "*"
        Action = [
          "s3:GetObject"
        ]
        Resource = [
          "${aws_s3_bucket.car_images_bucket.arn}/*"
        ]
      }
    ]
  })
}

resource "aws_db_instance" "postgres_db" {

  identifier = "car-inspection-db"

  allocated_storage = 20
  storage_type      = "gp2"

  engine = "postgres"

  instance_class = "db.t3.micro"

  db_name  = var.db_name
  username = var.db_username
  password = var.db_password

  publicly_accessible = true
  skip_final_snapshot = true

  backup_retention_period = 0
  multi_az                = false
  storage_encrypted       = false

  vpc_security_group_ids = []

  lifecycle {
    ignore_changes = [
      vpc_security_group_ids
    ]
  }

  tags = {
    Name = "Car Inspection PostgreSQL DB"
  }
}

# Backend ECR Repository
resource "aws_ecr_repository" "backend_repo" {
  name = "car-inspection-backend"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "Backend ECR"
  }
}

# Frontend ECR Repository
resource "aws_ecr_repository" "frontend_repo" {
  name = "car-inspection-frontend"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "Frontend ECR"
  }
}